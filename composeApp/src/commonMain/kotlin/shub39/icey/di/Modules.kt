package shub39.icey.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import shub39.icey.viewmodels.GameViewModel
import shub39.icey.viewmodels.Statelayer

val appModules = module {
    singleOf(::Statelayer)
    viewModelOf(::GameViewModel)
}