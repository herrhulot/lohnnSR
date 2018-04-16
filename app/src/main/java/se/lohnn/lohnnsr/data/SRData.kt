package se.lohnn.lohnnsr.data

data class SRData(val copyright: String, val programs: List<Program>)

data class Program(val name: String, val programimage: String, val description: String)