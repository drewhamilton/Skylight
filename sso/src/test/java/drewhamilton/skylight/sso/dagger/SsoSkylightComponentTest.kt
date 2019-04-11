package drewhamilton.skylight.sso.dagger

import org.junit.Assert.assertEquals
import org.junit.Test

class SsoSkylightComponentTest {

    @Test
    fun `builder returns Dagger builder`() {
        assertEquals(DaggerSsoSkylightComponent.builder()::class, SsoSkylightComponent.builder()::class)
    }
}
