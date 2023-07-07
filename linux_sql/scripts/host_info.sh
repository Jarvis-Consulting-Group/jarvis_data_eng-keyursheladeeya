#!/bin/bash

# Setup and validate arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Check the number of arguments
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

# Collect hardware information
hostname=$(hostname -f)
cpu_number=$(lscpu | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(lscpu | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(lscpu | egrep "^Model name:" | awk -F: '{print $2}' | xargs)
cpu_mhz=$(lscpu | egrep "^CPU MHz:" | awk '{print $3}' | xargs)
L2_cache=$(lscpu | egrep "^L2 cache:" | awk -F: '{print $2}' | sed 's/[^0-9]*//g' | xargs)
total_mem=$(vmstat --unit M | tail -1 | awk '{print $4}')
timestamp=$(date -u +"%Y-%m-%d %H:%M:%S")

# Construct the INSERT statement
insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, L2_cache, total_mem, timestamp)
VALUES ('$hostname', $cpu_number, '$cpu_architecture', '$cpu_model', $cpu_mhz, $L2_cache, $total_mem, '$timestamp');"

# Execute the INSERT statement
export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

# Exit the script
exit $?
