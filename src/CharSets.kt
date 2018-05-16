const val separator = "->"
const val epsilon = "ε"
const val epsilonChar = 'ε'
val endSet = createEndSet()
val unendSet = createUnendSet()

fun createUnendSet():HashSet<Char>{
    val set = HashSet<Char>()
    for (x in 'A'..'Z')
        set.add(x)
    return set
}

fun createEndSet():HashSet<Char>{
    val endWord = arrayOf('(',')','ε','+','-','*','/')
    val set = HashSet<Char>()
    for (x in 'a'..'z')
        set.add(x)
    set.addAll(endWord.toHashSet())
    return set
}