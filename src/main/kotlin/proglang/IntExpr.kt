package proglang

sealed interface IntExpr {
    class Add(val lhs: IntExpr, val rhs: IntExpr) : IntExpr {
        override fun toString(): String {
            return "$lhs + $rhs"
        }
    }

    class Literal(val value: Int): IntExpr {
        override fun toString(): String {
            return value.toString()
        }
    }

    class Var(val name: String): IntExpr {
        override fun toString(): String {
            return name
        }
    }

    class Mul(val lhs: IntExpr, val rhs: IntExpr): IntExpr {
        override fun toString(): String {
            return "$lhs * $rhs"
        }
    }

    class Sub(val lhs: IntExpr, val rhs: IntExpr): IntExpr {
        override fun toString(): String {
            return "$lhs - $rhs"
        }
    }

    class Div(val lhs: IntExpr, val rhs: IntExpr): IntExpr {
        override fun toString(): String {
            return "$lhs / $rhs"
        }
    }

    class Fact(val expr: IntExpr): IntExpr {
        override fun toString(): String {
            return "$expr!"
        }
    }

    class Paren(val expr: IntExpr): IntExpr {
        override fun toString(): String {
            return "($expr)"
        }
    }
}

fun IntExpr.eval(store: Map<String, Int>): Int = when (this) {
    is IntExpr.Add -> lhs.eval(store) + rhs.eval(store)
    is IntExpr.Literal -> value
    is IntExpr.Var -> store[name] ?: throw UndefinedBehaviourException("No value provided for variable $name")
    is IntExpr.Mul -> lhs.eval(store) * rhs.eval(store)
    is IntExpr.Sub -> lhs.eval(store) - rhs.eval(store)
    is IntExpr.Div -> if (rhs.eval(store) == 0) {
        throw UndefinedBehaviourException("Cannot divide by 0")
    } else { lhs.eval(store) / rhs.eval(store)  }
    is IntExpr.Fact -> {
        val expression = expr.eval(store)
        if (expression < 0) {
            throw UndefinedBehaviourException("Cannot take factorial of negative number")
        } else if (expression == 0) {
            1 } else {
            (IntExpr.Mul(expr, IntExpr.Fact(IntExpr.Sub(expr, IntExpr.Literal(1))))).eval(store)
        }
    }
    is IntExpr.Paren -> expr.eval(store)
}
