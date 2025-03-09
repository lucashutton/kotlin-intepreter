package proglang

const val CONDITIONAL_INDENTATION: Int = 4

interface Stmt {
    var next: Stmt?

    val lastInSequence: Stmt
        get() {
            var current = this
            while (current.next != null) {
                current = current.next!!
            }
            return current
        }

    abstract fun toString(indent: Int): String

    class Assign(
        val name: String,
        val expr: IntExpr,
        override var next: Stmt? = null): Stmt {
            override fun toString(indent: Int): String {
                var output = ""
                for (i in 0..<indent) {
                    output += " "
                }
                output += "$name = $expr\n"
                if (next != null) {
                    output += next!!.toString(indent)
                }
                return output
            }

            override fun toString() = toString(0)
    }

    class If(
        val condition: BoolExpr,
        val thenStmt: Stmt,
        val elseStmt: Stmt? = null,
        override var next: Stmt? = null): Stmt {
            override fun toString(indent: Int): String {
                var output = ""
                for (i in 0..<indent) {
                    output += " "
                }
                output += "if ($condition) {\n" + thenStmt.toString(indent + CONDITIONAL_INDENTATION)
                if (elseStmt != null) {
                    for (i in 0..<indent) {
                        output += " "
                    }
                    output += "} else {\n" + elseStmt.toString(indent + CONDITIONAL_INDENTATION)
                }
                for (i in 0..<indent) {
                    output += " "
                }
                output += "}\n"
                if (next != null) {
                    output += next!!.toString(indent)
                }
                return output
            }

            override fun toString() = toString(0)
    }
}