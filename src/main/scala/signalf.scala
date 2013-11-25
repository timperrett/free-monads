package example 

import scalaz.{~>,Id,Free,Functor}, Free.Return, Free.Suspend, Id.Id

// Signal models:
// http://en.wikipedia.org/wiki/Traffic_light

// needs to be covarient because of scalaz.Free
sealed trait SignalF[+A] 

object Signaling {
  sealed trait Aspect
  case object Green extends Aspect
  case object Amber extends Aspect
  case object Red   extends Aspect

  sealed trait Mode
  case object Off      extends Mode
  case object Flashing extends Mode
  case object Solid    extends Mode

  case class Signal(isOperational: Boolean, display: Map[Aspect, Mode])

  object Signal {
    import scalaz.syntax.state._
    import scalaz.State, State._

    type ->[A,B] = (A,B)
    type SignalState[A] = State[Signal,A]

    val default = Signal(
      isOperational = false, 
      display = Map(Red -> Flashing, Amber -> Off, Green -> Off))

    // make the signal avalible for use
    def enable: State[Signal, Boolean] = 
      for {
        a <- init
        _ <- modify((s: Signal) => s.copy(isOperational = true))
        r <- get
      } yield r.isOperational

    // combinators for valid states the signal can be in.
    def halt  = change(Red -> Solid, Amber -> Off,   Green -> Off)
    def ready = change(Red -> Solid, Amber -> Solid, Green -> Off)
    def go    = change(Red -> Off,   Amber -> Off,   Green -> Solid)
    def slow  = change(Red -> Off,   Amber -> Solid, Green -> Off)

    private def change(seq: Aspect -> Mode*): State[Signal, Map[Aspect, Mode]] = 
      for {
        m <- init
        _ <- modify(display(seq.toSeq))
        signal <- get
      } yield signal.display

    // TODO: requires validation to prevent invalid state changes
    private def display(seq: Seq[Aspect -> Mode]): Signal => Signal = signal => 
      if(signal.isOperational)
        signal.copy(display = signal.display ++ seq.toMap)
      else default
  }

  def main(args: Array[String]): Unit = {
    import Signal._
    import scalaz.State.{get => current}

    val program = for {
      _ <- enable
      _ <- halt
      r <- current
    } yield r

    println(program.eval(default))
  }
  


  case class Change[A](aspect: Aspect, mode: Mode, hole: A) extends SignalF[A]
}


object foo {
  import scalaz.Coproduct

  // val x: Coproduct[SignalState, LogF] = ???
  // Coproduct(Signal.enable \/ )

}