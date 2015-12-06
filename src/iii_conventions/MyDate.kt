package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = addTimeIntervals(timeInterval, 1)

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval) =
        addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.timeRepeated)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(number: Int) = RepeatedTimeInterval(this, number)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {

    fun contains(date: MyDate) = start <= date && endInclusive >= date

    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start

            override fun next(): MyDate {
                val aux = current
                current = current.nextDay()

                return aux
            }

            override fun hasNext(): Boolean {
                return current <= endInclusive
            }
        }
    }
}

data class RepeatedTimeInterval(val timeInterval: TimeInterval, val timeRepeated: Int)