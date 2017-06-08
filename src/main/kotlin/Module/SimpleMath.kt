package Module

/**
 * Created by jacob on 2017-06-08 (YYYY-MM-DD).
 */
class SimpleMath: AbstractModule() {
    override val CommandPrefixes = arrayOf("math", "stuff")

    override fun OnMessage(message: String) {
        println(message)
    }
}