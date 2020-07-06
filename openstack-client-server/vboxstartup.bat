@echo off

set arg1=%1

if %arg1%==up (

VBoxManage controlvm openstack_controller_2 poweroff
VBoxManage controlvm openstack_compute1_2 poweroff
VBoxManage controlvm openstack_compute2_2 poweroff
VBoxManage controlvm openstack_block1_2 poweroff

VBoxManage snapshot openstack_controller_2 restore syslog
VBoxManage snapshot openstack_compute1_2 restore syslog
VBoxManage snapshot openstack_compute2_2 restore syslog
VBoxManage snapshot openstack_block1_2 restore syslog

VBoxManage startvm openstack_controller_2 --type headless
VBoxManage startvm openstack_compute1_2 --type headless
VBoxmanage startvm openstack_compute2_2 --type headless
VBoxManage startvm openstack_block1_2 --type headless

)

if %arg1%==down (

VBoxManage controlvm openstack_controller_2 poweroff
VBoxManage controlvm openstack_compute1_2 poweroff
VBoxManage controlvm openstack_compute2_2 poweroff
VBoxManage controlvm openstack_block1_2 poweroff

)