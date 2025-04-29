package com.example.loginregistrationfragmentapp.utils

class const {
    object VmState {
        const val UNKNOWN = 0        // ICON: vm-error TEXT: Неизвестно
        const val POWER_OFF = 1      // ICON: vm TEXT: Выключено
        const val POWER_ON = 2       // ICON: vm-on TEXT: Включено
        const val SUSPENDED = 3      // ICON: vm-suspended TEXT: Приостановлено
        const val ERROR = 4          // ICON: vm-error TEXT: Ошибка
        const val WARNING = 5        // ICON: vm-warning TEXT: Предупреждение
        const val PENDING = 6        // ICON: vm-warning TEXT: Ожидание
        const val PAUSED = 7         // ICON: vm-suspended TEXT: Пауза
        const val BLOCKED = 8        // ICON: vm-warning TEXT: Заблокировано
        const val SHUTTING_DOWN = 9  // ICON: vm-warning TEXT: Завершение работы
        const val SHUTTING_OFF = 10  // ICON: vm-warning TEXT: Выключение
    }
}