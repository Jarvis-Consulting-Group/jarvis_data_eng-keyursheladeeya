# Linux Cluster Monitoring Agent

## Introduction
Linux Server Monitoring Agent is a tool that monitor connected nodes in a Linux Cluster. The project is designed to install on each server and collect data automatically. The monitoring agent was installed on each node, it automatically collects hardware information and resource usage data from the server and persist it to the PostgreSQL database to perform data analytics. The users of this project would be system administrators or IT personnel who are responsible for managing servers. The technologies used in this project are bash scripts, PostgreSQL, docker, git, GitHub, notion scrum board, and remote desktop with Linux centos.

## Scripts

- [psql_docker.sh](https://github.com/Jarvis-Consulting-Group/jarvis_data_eng-keyursheladeeya/blob/develop/linux_sql/scripts/psql_docker.sh)
to create | start | stop the docker conatiner for psql instance
```
# script usage
./scripts/psql_docker.sh start|stop|create [db_username][db_password]
```

- [ddl.sql](https://github.com/Jarvis-Consulting-Group/jarvis_data_eng-keyursheladeeya/blob/develop/linux_sql/sql/ddl.sql) 
to automate the database tables initialization

```
# Executed ddl.sql script on the host_agent database
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```

- [host_info.sh](https://github.com/Jarvis-Consulting-Group/jarvis_data_eng-keyursheladeeya/blob/develop/linux_sql/scripts/host_info.sh) 
to collect hardware specifications and insert them into the psql instance
```
# Script usage
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```

- [host_usage.sh](https://github.com/Jarvis-Consulting-Group/jarvis_data_eng-keyursheladeeya/blob/develop/linux_sql/scripts/host_usage.sh)
to collect server usage data and insert it into the psql database
```
# Script usage
bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```

- Linux's crontab setup to execute script every minute 

## Database and Tables
The database consists of two tables such as host_info and host_usage to collect hardware information and resource usage data from the server.

The `host_info` table consists of the following information:
```
- id              
- hostname       
- cpu_number       
- cpu_architecture 
- cpu_model       
- cpu_mhz      
- l2_cache        
- timestamp     
- total_mem 
```

The `host_usage` table consists of the following information and is added to each node every minute, in order to keep information up-to-date.
```
- timestamp              
- host_id       
- memory_free       
- cpu_idel 
- cpu_kernel       
- disk_io      
- disk_available        
```  

## Deployment
- deploy the Linux Server Monitoring Agent app to the remote desktop and collect data every minute automatically and varified it.
```
  #edit crontab job
  crontab -e
  
  #crontab job
   * * * * * bash /home/centos/dev/jarvis_data_eng-keyursheladeeya/linux_sql/host_agent/scripts/host_usage.sh localhost 5432 host_agent postgres password > /tmp/host_usage.log
   
  #varify the log file
  cat /tmp/host_usage.log
```

## Improvements
- The Linux Server Monitoring Agent could potentially improve the efficiency of managing servers by automating the process of collecting hardware specifications. 
- The ease of installation and automatic data collection will make this program more user-friendly and convenient for system administrators and IT personnel to use.
- By storing data in a PostgreSQL database, it would also make it easier to analyze and retrieve the data for troubleshooting or performance monitoring purposes.
