package ru.emacs



import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfiguration {
    companion object{
        const val SECURITY_PROPERTIES_CACHE="SecurityProperties"
    }
    @Bean("cacheManager")
    fun cacheManager(): CacheManager {
        return ConcurrentMapCacheManager(SECURITY_PROPERTIES_CACHE)
    }
}