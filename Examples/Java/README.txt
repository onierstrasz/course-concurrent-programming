
These are examples for the Concurrent Programming course (CP).

# 2. Java and Concurrency
- ThreadDemo: simple.SimpleThread -- two interleaving threads compete for the print line
- Clock: clock.Clock -- A digital clock with an asynchronously running thread to update the display
- Account: bank.Account -- Delays withdrawals until enough money is there.
  AccountBAD illustrates potential race condition

# 3. Safety and Synchronization
- Account: AccountBAD illustrates race condition
- Slot: ProducerConsumerDemo -- Multiple producers and consumers synchronize via a shared slot

# 4. Safety Patterns
- Complex: immutable complex numbers
- Counter: BalkingBoundedCounter full synchronization with busy-waiting
- ExpandableArray: full synchronization with atomic batched action
- LinkedCell: partial synchronization
- Ownership: resource variable

# 5. Liveness and Guarded Methods
- Counter: BoundedCounterBasic basic synchronization with wait & notifyAll
- Counter: BoundedCounterNoSyncBAD race condition
- Counter: BoundedCounterEncapsulatedAssigns uses helper methods
- Counter: BoundedCounterTrackingState tracks logical state
- Counter: BoundedCounterStateVariables uses state variables
- Counter: BoundedCounterNotifyingLong uses helper object to notify state changes

# 7. Liveness and Asynchrony
- Asynchrony: HostDirectRelay, HostDirectRelaySyncHelper etc illustrate asynchronous invocations
- Asynchrony: TailCallSubject and TailCallSubjectHelperThread show tail calls with (i) no synchronization, and (ii) new Thread
- Asynchrony: EarlyReplyDemo -- demos how to return a result, yet hold the lock during cleanup
- Asynchrony: Future -- how to obtain a ticket for an ongoing computation
- Asynchrony: FutureTaskDemo -- Future demo translated to JUC

# 8. Condition Objects
- Counter: BoundedCounterNestedMonitorBAD and BoundedCounterCondition
illustrate Nested Monitors and solution
- Counter: Permit and Room illustrates counting condition
- Counter: Semaphore -- simple version (contrast juc.Semaphore)
- Counter: BoundedCounterVSem -- BC implemented with Semaphores
- Counter: BoundedCounterJUCSem -- BC implemented with JUC Semaphores

# 9. Fairness and Optimism
- ReadersWriters: ReadWriteDemo
- Counter: BoundedCounterVOPT

# 13. Architectural Styles
- Activeprimes: PrimeSieve -- primes as active objects
- JavaSpaces: FibDemo -- compute Fibonacci numbers with a Tuple Space
