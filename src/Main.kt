import kotlin.system.exitProcess

val rawDataMap : MutableMap<Char,RawData> = LinkedHashMap()
val firstMap = LinkedHashMap<Char,HashSet<Char>>()
val followMap = LinkedHashMap<Char,HashSet<Char>>()

lateinit var rawDataList : List<Pair<Char,RawData>>

fun main(args: Array<String>) {
    readRawData()
    getFirstSet()
    println("\nFIRST集")
    firstMap.forEach {
        println(it)
    }
    getFollowSet()
    println("\nFollow集")
    followMap.forEach {
        println(it)
    }
}

fun getFirstSet(){
    println("开始计算First集")
    rawDataList = rawDataMap.toList()
    rawDataList.forEach {
        calculateFirstSet(it.first)
    }
}

fun getFollowSet(){
    println("开始计算Follow集")
    followMap[rawDataList[0].first] = hashSetOf<Char>('#')
    rawDataList.forEach {
        calculateFollowSet(char = it.first)
    }
}

fun calculateFollowSet(char: Char){
    val set : HashSet<Char> = if (followMap.containsKey(char))
            followMap[char]!!
        else
            HashSet()
    for (x in rawDataList){
        val rawData = x.second
        rawData.datas.forEach {
            var index = 0
            val length = it.length
            while (index < length){
                if ( it[index] == char){
                    if (index == length-1){
                        if (char == rawData.element)
                            break
                        calculateFollowSet(rawData.element)
                        val charSet = followMap[rawData.element]!!
                        set.addAll(charSet)
                    }else if ( endSet.contains(it[index+1]) ){
                        set.add(it[index+1])
                    }else{
                        index++
                        var tempSet : HashSet<Char>
                        while (index < length){
                            if (endSet.contains(it[index])){
                                set.add(it[index])
                                break
                            }
                            tempSet = firstMap[it[index]]!!
                            if (tempSet.contains(epsilonChar)){

                                tempSet.forEach {
                                    if ( it != epsilonChar)
                                        set.add(it)
                                }

                                if (index == length-1){
                                    if (char == rawData.element)
                                        break
                                    calculateFollowSet(rawData.element)
                                    val charSet = followMap[rawData.element]!!
                                    set.addAll(charSet)
                                    break
                                }
                            }
                            index++
                        }
                    }
                }
                index++
            }
        }
    }
    followMap[char] = set
}

fun calculateFirstSet(char: Char){
    if (firstMap.containsKey(char))
        return
    val set = HashSet<Char>()
    val rawData = rawDataMap[char]!!
    var containEpsilon = true
    rawData.datas.forEach {
        if (endSet.contains(it[0]))
            set.add(it[0])
        else{
            var p = 0
            val length = it.length
            while ( p < length){
                if (endSet.contains(it[p]))
                    break
                calculateFirstSet(it[p])
                val tempSet = firstMap[it[p]]!!
                set.addAll(tempSet)
                if (!tempSet.contains(epsilonChar)){
                    containEpsilon = false
                    return@forEach
                }
                p++
            }
        }
    }
    if (!containEpsilon)
        set.remove(epsilonChar)
    firstMap[char] = set
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
    while(count < size){
        val t = readLine()!!
        val list = t.split(separator)
        val element = list[0][0]
        val datas = list[1]
        if (!rawDataMap.containsKey(element)){
            val rawData = RawData(element,datas = datas.split("|").toMutableList())
            rawDataMap[element] = rawData
        }else{
            rawDataMap[element]!!.datas.add(datas)
        }
        count++
    }
    println("原始元素输出")
    rawDataMap.forEach { println(it) }
}