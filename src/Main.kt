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

fun getForecastAnalysisTable(){
    println("开始计算预测分析表")
    val endChar = getEndCharFromRawDataList()
    val itemCount = rawDataList.size
    val width = endChar.size
    val table : Array<Array<RawData?>> = initTable(itemCount = itemCount,width = width)

    var index = 1
    while ( index < itemCount+1){
        table[index][0] = RawData(rawDataList[index-1].first, null)
        index++
    }
    index = 1
    while ( index < width+1){
        table[0][index] = RawData(endChar[index-1], null)
        index++
    }
    val indexMap = HashMap<Char,Int>()
    endChar.forEachIndexed { ind, c -> indexMap[c] = ind }
    index = 1
    while ( index < itemCount+1){
        val targetChar = table[index][0]!!.element
        val rawData = rawDataMap[targetChar]!!
        var hasEpsilon = false
        val firstSet = firstMap[targetChar]
        firstSet!!.forEach {
            val firstIndex = indexMap[it]!!

        }
    }
}



private fun initTable(itemCount : Int, width : Int):Array<Array<RawData?>>{
    val table : Array<Array<RawData?>> = Array<Array<RawData?>>(itemCount+1){
        Array(width+1){
            null
        }
    }
    return table
}

fun getEndCharFromRawDataList():ArrayList<Char>{
    val endChar = ArrayList<Char>()
    rawDataList.forEach {
        it.second.datas!!.forEach {
            it.forEach {
                if (endSet.contains(it))
                    endChar.add(it)
            }
        }
    }
    return endChar
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
        rawData.datas!!.forEach {
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
    rawData.datas!!.forEach {
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
            rawDataMap[element]!!.datas!!.add(datas)
        }
        count++
    }
    println("原始元素输出")
    rawDataMap.forEach { println(it) }
}