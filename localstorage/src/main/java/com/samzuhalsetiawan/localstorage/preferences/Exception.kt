package com.samzuhalsetiawan.localstorage.preferences

class UnSupportedPreferencesType(className: String)
    : Exception("Unsupported preferences type: $className")