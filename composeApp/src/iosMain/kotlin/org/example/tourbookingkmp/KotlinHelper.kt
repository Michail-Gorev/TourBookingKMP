package org.example.tourbookingkmp

import org.example.tourbookingkmp.di.initKoin
import kotlin.experimental.ExperimentalObjCName

object KoinHelper {

    @OptIn(ExperimentalObjCName::class)
    @ObjCName("doInitKoinFromSwift")
    fun doInitKoinFromSwift() {
        initKoin()
    }
}