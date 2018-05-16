
data class RawData(val element : Char, val datas : MutableList<String>){
    override fun toString(): String {
        return "RawData(element=$element, datas=$datas)"
    }
}
