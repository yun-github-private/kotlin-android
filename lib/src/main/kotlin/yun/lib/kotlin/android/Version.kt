package yun.lib.kotlin.android

class Version private constructor(private val numbers: List<Int>, val name: String) {

    operator fun compareTo(other: Version): Int {
        if (numbers != other.numbers) {
            numbers.mapIndexed { index, value -> value.compareTo(other.numbers[index]) }
                    .filter { it != 0 }
                    .forEach { return it }
        }
        return 0
    }

    override fun hashCode(): Int {
        return numbers.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Version) return false
        return numbers == other.numbers
    }

    companion object {
        private const val MAX_LENGTH = 4
        /**
         * @param name "{メジャー}.{マイナー}.{ビルド}.{リビジョン}"
         * @return
         * <ul>
         *     <li>"1.0.0.0" --> not null</li>
         *     <li>"1.0" --> not null</li>
         *     <li>"" --> null</li>
         *     <li>"1.0.beta" --> null</li>
         *     <li>"1.0.0.0.0" --> null</li>
         * </ul>
         */
        fun of(name: String): Version? {
            val numbers = MutableList(MAX_LENGTH) { 0 }
            val strings = name.split("\\.".toRegex())
                    .apply { if (size > MAX_LENGTH) return null }
            return try {
                strings.mapIndexed { index, value -> numbers[index] = value.toInt() }
                Version(numbers, name)
            } catch (e: NumberFormatException) {
                null
            }
        }
    }
}
