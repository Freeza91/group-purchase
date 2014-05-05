# db_config

ActiveRecord::Base.establish_connection(
	:adapter 	=> 'mysql2',
  :host     => 'localhost',
  :username => 'root',
  :password => 'root',
	:database => 'group_purchase',
	:encoding	=> 'utf8'
)

#ActiveRecord::Base.default_timezone = :beijing

ActiveRecord::Base.default_timezone = :local
