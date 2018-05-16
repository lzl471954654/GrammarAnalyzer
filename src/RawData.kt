
data class RawData(val element: Char, val datas: MutableList<String>?) {
    var hasEpsilon = false
    var epsilonIndex = -1

    override fun toString(): String {
        return "RawData(element=$element, datas=$datas)"
    }

    fun getTotalWordWidth():Int{
        return if (datas == null)
            1
        else{
            var size = 0
            datas.forEach { size+=it.length }
            3+size
        }
    }

    init {
        datas?.forEachIndexed { index, s ->
            if (s.contains(epsilonChar)){
                hasEpsilon = true
                epsilonIndex = index
            }
        }
    }
}
