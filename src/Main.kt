import kotlin.system.exitProcess

const val separator = "->"
const val epsilon = "ε"
lateinit var rawDataList : MutableList<RawData>

fun main(args: Array<String>) {
    readRawData()
}

fun readRawData(){
    println("Please enter the number of grammar.")
    val temp = readLine()
    if (temp.isNullOrEmpty()){
        println("Not a Number!")
        exitProcess(1)
    }
    val size = temp!!.toInt()
    var count = 0
    rawDataList = ArrayList()
    while(count < size){
        val t = readLine()!!
        val list = t.split(separator)
        //println(list)
        val elment = list[0][0]
        val datas = list[1]
        val rawData = RawData(elment,datas = datas.split("|"))
        rawDataList.add(rawData)
        count++
    }
    rawDataList.forEach { println(it) }
}