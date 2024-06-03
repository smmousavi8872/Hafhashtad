package com.github.smmousavi.hafhashtad

import androidx.lifecycle.ViewModel
import com.github.smmousavi.repository.repo.store.DefaultOfflineFirstStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(storeRepository: DefaultOfflineFirstStoreRepository) :
    ViewModel() {

}