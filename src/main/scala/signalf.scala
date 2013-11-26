package example 

import scalaz.{~>,Id,Free,Functor}, Free.Return, Free.Suspend, Id.Id

// Signal models:
// http://en.wikipedia.org/wiki/Traffic_light

// needs to be covarient because of scalaz.Free
sealed trait SignalF[+A] 

object foo {
  import scalaz.Coproduct

  // val x: Coproduct[SignalState, LogF] = ???
  // Coproduct(Signal.enable \/ )

}