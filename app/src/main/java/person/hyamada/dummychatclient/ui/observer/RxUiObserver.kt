package person.hyamada.dummychatclient.ui.observer

import android.widget.Button
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

fun createObserableFromButton(button : Button): Observable<Boolean> {
    val subject: PublishSubject<Boolean> = PublishSubject.create()
    button.setOnClickListener { _ ->
        subject.onComplete()
    }
    return subject
}
