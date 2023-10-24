package com.sagrd.kevin_p1_ap2.util.nav

sealed class AppScreens(val route : String)
{
    object ConsultScreen: AppScreens("consult_Screen")
    object FormScreen: AppScreens("form_Screen")

}