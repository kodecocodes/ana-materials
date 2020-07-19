package com.raywenderlich.wewatch

import org.mockito.ArgumentCaptor

open class BaseTest {

  /**
   * Returns ArgumentCaptor.capture() as nullable type to avoid java.lang.IllegalStateException
   * when null is returned.
   */
  open fun <T> captureArg(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()

}
