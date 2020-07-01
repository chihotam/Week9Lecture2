package tests

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import bank.{Balance, BankAccount, CheckBalance, Deposit, Withdraw}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._

class TestBankAccount extends TestKit(ActorSystem("Test"))
  with ImplicitSender
  with WordSpecLike
  with Matchers
  with BeforeAndAfterAll {
  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }
  "A value actor" must {
    "track a value" in {
      val bank = system.actorOf(Props(classOf[BankAccount]))
      bank ! Deposit(10)
      bank ! Deposit(20)
      bank ! Withdraw(40)
      bank ! Withdraw(10)
      bank ! Deposit(15)
      expectNoMessage(100.millis)
      bank ! CheckBalance
      val balance: Balance = expectMsgType[Balance](1000.millis)
      assert(balance == Balance(35))
    }
  }
}
