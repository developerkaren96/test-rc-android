package com.example.loginregistrationfragmentapp.vmlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Deployment(
    val deployment_name: String,
    val name: String,
    val uuid: String,
    val compatibility: String,
    val guest_os_family: String,
    val guest_os_version: String,
    val storage: Storage,
    val machine_type: String,
    val cpu: CPU,
    val memory: Memory,
    val video_card: VideoCard,
    val usb_controllers: List<USBController>,
    val input_devices: List<InputDevice>,
    val disk_devices: List<DiskDevice>,
    val network_devices: List<NetworkDevice>,
    val passthrough_pci_devices: List<PassthroughPCIDevice>,
    val options: Options,
    val monitoring: Monitoring,
) : Parcelable

@Parcelize
data class Storage(
    val id: String,
    val folder: String
) : Parcelable

@Parcelize
data class CPU(
    val vcpus: Int,
    val max_vcpus: Int,
    val core_per_socket: Int,
    val model: String,
    val reservation_mhz: Int
) : Parcelable

@Parcelize
data class Memory(
    val size_mb: Int,
    val hotplug: Boolean,
    val reservation_mb: Int
) : Parcelable

@Parcelize
data class VideoCard(
    val adapter: String,
    val displays: Int,
    val memory_mb: Int
) : Parcelable

@Parcelize
data class USBController(
    val type: String
) : Parcelable

@Parcelize
data class InputDevice(
    val type: String,
    val bus: String
) : Parcelable

@Parcelize
data class DiskDevice(
    val size: Int?, //Общий ресурс
    val source: String, //.sdk
    val device_type: String,
    val bus: String, //sata
    val target: String //vda
) : Parcelable

@Parcelize
data class NetworkDevice(
    val network: String,
    val mac: String,
    val model: String
) : Parcelable

@Parcelize
data class PassthroughPCIDevice(
    val address: String,
    val vendor_name: String,
    val class_name: String,
    val device_name: String
) : Parcelable

@Parcelize
data class Options(
    val remote_console: RemoteConsole
) : Parcelable

@Parcelize
data class RemoteConsole(
    val type: String,
    val port: Int,
    val keymap: String
) : Parcelable

@Parcelize
data class Monitoring(
    val state: Int,
    val guest_tools: MonitoringGuestTools,
    val cpu: MonitoringCPU,
    val storage: MonitoringStorage,
    val memory: MonitoringMemory
) : Parcelable

@Parcelize
data class MonitoringGuestTools(
    val status: String, //"running"
    val version: String, //"108.0.2"
    val ip: String, //" fe80::62ff:c90f:94c4:d519%13, 10.10.101.195"
    val dns_name: String //"DESKTOP-0O7EN2N"
) : Parcelable

@Parcelize
data class MonitoringCPU(
    val used_mhz: Double,
) : Parcelable

@Parcelize
data class MonitoringStorage(
    val used_space_mb: Double,
) : Parcelable

@Parcelize
data class MonitoringMemory(
    val used_mb: Double,
) : Parcelable

