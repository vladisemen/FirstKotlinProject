fun main(args: Array<String>){
    var List: Array<String>
    //region 6 задание
    if(args.size==0){
        val Str1: String = readLine().toString()
        List = Str1.split(" ").toTypedArray()
    }else{
        List = args
    }
    //endregion

    //region 1 задание
    println("Задание 1:")
    for (i in List){
        println(i)
    }
    //endregion
    //region 2 задание
    println("Задание 2:")
    var ListSort = List.sorted()
    for (i in ListSort){
        println(i)
    }
    //endregion
    //region 3 задание
    println("Задание 3:")
    val ListUnique : MutableList<String> = mutableListOf()
    var Check1: Boolean = true
    for(element in ListSort){
        Check1 = !ListUnique.contains(element)
        if (Check1){
            ListUnique.add(element)
        }
    }
    for (i in ListUnique){
        println(i)
    }
    //endregion
    //region 4 задание
    println("Задание 4:")
    var MapCollection: MutableMap<String, Int> = mutableMapOf()
    var CountRepeat: Int = 0
    for(element in ListUnique){
        CountRepeat = 0
        for (element1 in ListSort){
            if (element == element1){
                CountRepeat++
            }
        }
        MapCollection.put(element, CountRepeat)
    }
    for (i in MapCollection){
        println(i.key+" "+ i.value)
    }
    //endregion
    //region 5 задание
    val result = MapCollection.toList().sortedByDescending { (_, value) -> value}.toMap()
    println("Задание 5:")
    for (i in result){
        println(i.key+" "+ i.value)
    }
    //endregion
}
