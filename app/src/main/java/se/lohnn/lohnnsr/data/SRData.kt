package se.lohnn.lohnnsr.data

import java.io.Serializable

data class SRData(val copyright: String, val programs: List<Program>)

data class Program(val name: String, val description: String, val programimage: String, val programimagewide: String) : Serializable