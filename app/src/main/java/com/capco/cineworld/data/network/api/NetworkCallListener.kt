package com.capco.cineworld.data.network.api

import com.capco.cineworld.data.network.CallInfo

interface NetworkCallListener {
    fun onNetworkCallStarted(callInfo: CallInfo)
    fun onNetworkCallSuccess(callInfo: CallInfo)
    fun onNetworkCallFailure(callInfo: CallInfo)
    fun onNetworkCallCancel(callInfo: CallInfo)
}