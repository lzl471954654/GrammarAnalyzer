import java.util.*

data class RawData(val element : Char, val datas : List<String>){
    override fun toString(): String {
        return "RawData(element=$element, datas=$datas)"
    }
}