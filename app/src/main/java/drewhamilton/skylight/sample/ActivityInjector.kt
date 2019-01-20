package drewhamilton.skylight.sample

interface ActivityInjector {

    fun inject(mainActivity: MainActivity)

    companion object {
        lateinit var instance: ActivityInjector
    }
}
