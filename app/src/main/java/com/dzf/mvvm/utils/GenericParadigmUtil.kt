package com.dzf.mvvm.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.*

/**
 * @ProjectName : MVVM
 * @Author : Dai Zhi Feng
 * @Time : 2021/10/28 10:01
 * @Description : 文件描述
 */
object GenericParadigmUtil {
    fun parseGenericParadigm(`object`: Any?, position: Int): Class<*>? {
        return if (`object` == null) {
            null
        } else parseGenericParadigm(`object`.javaClass, position)
    }

    private fun parseGenericParadigm(clazz: Class<*>?, position: Int): Class<*>? {
        if (clazz == null) {
            return null
        }
        val pathfinders: MutableList<Pathfinder> = ArrayList(1)
        pathfinders.add(ConsistentPathfinder(Int.MAX_VALUE, position))
        return parseGenericParadigm(clazz, pathfinders)
    }

    fun parseGenericParadigm(`object`: Any?, pathfinders: List<Pathfinder>?): Class<*>? {
        return if (`object` == null) {
            null
        } else parseGenericParadigm(
            `object`.javaClass,
            pathfinders
        )
    }

    fun parseGenericParadigm(clazz: Class<*>, pathfinders: List<Pathfinder>?): Class<*>? {
        if (!isGenericParadigm(clazz) || pathfinders == null || pathfinders.isEmpty()) {
            return null
        }
        assertPathfinder(pathfinders)
        val pathfinder = pathfinders[0]
        val isConsistentPathfinder = pathfinder is ConsistentPathfinder
        val size = pathfinders.size
        val type = clazz.genericSuperclass
        return getGenericClassPlus(type, 0, size, isConsistentPathfinder, pathfinders)
    }

    fun parseInterfaceGenericParadigm(`object`: Any?, who: Int, position: Int): Class<*>? {
        return if (`object` == null) {
            null
        } else parseInterfaceGenericParadigm(
            `object`.javaClass,
            who,
            position
        )
    }

    private fun parseInterfaceGenericParadigm(clazz: Class<*>?, who: Int, position: Int): Class<*>? {
        if (clazz == null) {
            return null
        }
        val pathfinders: MutableList<Pathfinder> = ArrayList(1)
        pathfinders.add(ConsistentPathfinder(Int.MAX_VALUE, position))
        return parseInterfaceGenericParadigm(clazz, who, pathfinders)
    }

    fun parseInterfaceGenericParadigm(
        `object`: Any?,
        who: Int,
        pathfinders: List<Pathfinder>?
    ): Class<*>? {
        return if (`object` == null) {
            null
        } else parseInterfaceGenericParadigm(
            `object`.javaClass,
            who,
            pathfinders
        )
    }

    private fun parseInterfaceGenericParadigm(
        clazz: Class<*>,
        who: Int,
        pathfinders: List<Pathfinder>?
    ): Class<*>? {
        if (!isInterfaceGenericParadigm(clazz) || pathfinders == null || pathfinders.isEmpty()) {
            return null
        }
        val genericInterfaces = clazz.genericInterfaces
        val length = genericInterfaces.size
        if (who < 0 || who >= length) {
            return null
        }
        assertPathfinder(pathfinders)
        val pathfinder = pathfinders[0]
        val isConsistentPathfinder = pathfinder is ConsistentPathfinder
        val size = pathfinders.size
        val type = genericInterfaces[who]
        return getGenericClassPlus(type, 0, size, isConsistentPathfinder, pathfinders)
    }

    fun isInterfaceGenericParadigm(`object`: Any?): Boolean {
        return if (`object` == null) {
            false
        } else isInterfaceGenericParadigm(`object`.javaClass)
    }

    private fun isInterfaceGenericParadigm(clazz: Class<*>?): Boolean {
        if (clazz == null) {
            return false
        }
        val genericInterfaces = clazz.genericInterfaces
        return genericInterfaces != null && genericInterfaces.size > 0
    }

    fun isGenericParadigm(`object`: Any?): Boolean {
        return if (`object` == null) {
            false
        } else isGenericParadigm(`object`.javaClass)
    }

    private fun isGenericParadigm(clazz: Class<*>?): Boolean {
        if (clazz == null) {
            return false
        }
        val genericSuperclass = clazz.genericSuperclass
        return genericSuperclass is ParameterizedType
    }

    private fun getGenericClassPlus(
        type: Type,
        level: Int,
        size: Int,
        isConsistentPathfinder: Boolean,
        pathfinders: List<Pathfinder>
    ): Class<*>? {
        if (isConsistentPathfinder || level < size) {
            // 得到指路人指明前进的道路
            val pathfinder = if (isConsistentPathfinder) pathfinders[0] else pathfinders[level]
            if (type is ParameterizedType) {
                val types = type.actualTypeArguments
                val length = types.size
                val position = pathfinder.position
                return if (position < 0 || position >= length) {
                    null
                } else getGenericClassPlus(
                    types[position],
                    level + 1,
                    size,
                    isConsistentPathfinder,
                    pathfinders
                )
            }
        }
        if (type is Class<*>) {
            return type
        } else if (type is ParameterizedType) {
            return type.rawType as Class<*>
        }
        return null
    }

    @Deprecated("")
    private fun getGenericClass(
        type: Type,
        level: Int,
        size: Int,
        isConsistentPathfinder: Boolean,
        pathfinders: List<Pathfinder>
    ): Class<*>? {
        if (isConsistentPathfinder) {
            // 特殊, 找到泛型的最深处类型
            if (type is Class<*>) {
                return type
            }
        } else {
            // 指定指路人
            if (level >= size) {
                if (type is Class<*>) {
                    return type
                } else if (type is ParameterizedType) {
                    return type.rawType as Class<*>
                }
            }
        }

        // 得到指路人指明前进的道路
        val pathfinder = if (isConsistentPathfinder) pathfinders[0] else pathfinders[level]
        return if (type is ParameterizedType) {
            val types = type.actualTypeArguments
            val length = types.size
            val position = pathfinder.position
            if (position < 0 || position >= length) {
                null
            } else getGenericClass(
                types[position],
                level + 1,
                size,
                isConsistentPathfinder,
                pathfinders
            )
        } else {
            null
        }
    }

    private fun assertPathfinder(pathfinders: List<Pathfinder>?) {
        require(!(pathfinders == null || pathfinders.isEmpty())) { "Oh, No, It`s not have Pathfinder..." }
        var pathfinder = pathfinders[0]
        val isConsistentPathfinder = pathfinder is ConsistentPathfinder
        if (!isConsistentPathfinder) {
            val size = pathfinders.size
            for (level in 0 until size) {
                pathfinder = pathfinders[level]
                require(level == pathfinder.depth) { "Oh, No, Pathfinders is incomplete..." }
            }
        }
    }

    private class ConsistentPathfinder(in_depth: Int, in_position: Int) : Pathfinder() {
        init {
            super.depth = in_depth
            super.position = in_position
        }
    }

    open class Pathfinder {
        var depth = 0
        var position = 0

        constructor() {}
        constructor(in_depth: Int, in_position: Int) {
            depth = in_depth
            position = in_position
        }
    }
}