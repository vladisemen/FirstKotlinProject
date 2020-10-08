fun main(args: Array<String>) {
    //region 6 задание
    val list: Array<String> =  if(args.isEmpty()) // если через параметр ничего не пришло, читаем поток stdin
    {readLine().toString().split(" ").toTypedArray()}
    else{args}
    //endregion
    //region 1 задание
    println("Задание 1:")
    list.forEach(::println)
    //endregion
    //region 2 задание
    println("Задание 2:")
    val listSort = list.sorted()
    listSort.forEach(::println)
    //endregion
    //region 3 задание
    println("Задание 3:")
    val listUnique: MutableList<String> = mutableListOf()
    var check1: Boolean = true
    for (element in listSort) {
        check1 = !listUnique.contains(element)
        if (check1) {
            listUnique.add(element)
        }
    }
    listUnique.forEach(::println)
    //endregion
    //region 4 задание
    println("Задание 4:")
    var mapCollection: MutableMap<String, Int> = mutableMapOf()
    var countRepeat: Int = 0
    for (element in listUnique) { // берем элемент с массива уникальных
        countRepeat = 0
        for (element1 in listSort) { // считаем кол-во соовпадений с фулл массивом
            if (element == element1) {
                countRepeat++
            }
        }
        mapCollection.put(element, countRepeat) // добавляем элемент с количесвтом его повторений в map-коллекцию
    }
    for (i in mapCollection) {
        println(i.key + " " + i.value)
    }
    //endregion
    //region 5 задание
    val result = mapCollection.toList().sortedByDescending { (_, value) -> value }.toMap()
    println("Задание 5:")
    for (i in result) {
        println(i.key + " " + i.value)
    }
    //endregion
}
