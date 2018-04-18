package se.lohnn.lohnnsr.data

import java.io.Serializable

data class SRData(val copyright: String, val programs: List<Program>)

data class Program(val name: String, val description: String, val programimage: String, val programimagewide: String, val socialmediaplatforms: List<SocialMedia>) : Serializable {
    fun facebook(): SocialMedia? {
        return socialmediaplatforms.firstOrNull { it.platform == "Facebook" }
    }

    fun instagram(): SocialMedia? {
        return socialmediaplatforms.firstOrNull { it.platform == "Instagram" }
    }

    fun twitter(): SocialMedia? {
        return socialmediaplatforms.firstOrNull { it.platform == "Twitter" }
    }
}

data class SocialMedia(val platform: String, val platformurl: String) : Serializable