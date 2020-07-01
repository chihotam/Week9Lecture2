package bank

import akka.actor._

case class Deposit(d: Int)
case class Withdraw(w: Int)
case class Balance(b: Int)
case object CheckBalance

class BankAccount extends Actor {
  var funds: Int = 0
  def receive: Receive = {
    case deposit: Deposit => funds += deposit.d
    case withdraw: Withdraw =>
      if(funds >= withdraw.w) {
        funds -= withdraw.w
      }
    case balance: Balance => println(balance.b)
    case CheckBalance => sender() ! Balance(funds)
  }
}