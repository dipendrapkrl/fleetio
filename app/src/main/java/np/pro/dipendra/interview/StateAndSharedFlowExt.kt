package np.pro.dipendra.interview

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

inline fun <T> StateFlow<T>.collect(owner: LifecycleOwner, crossinline result: (T) -> Unit) {
    val flow = this
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { result.invoke(it) }
        }
    }
}
inline fun <T> SharedFlow<T>.collect(owner: LifecycleOwner, crossinline result: (T) -> Unit) {
    val flow = this
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect { result.invoke(it) }
        }
    }
}