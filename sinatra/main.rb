#main

require 'sinatra'
require "sinatra/activerecord"
require 'yaml'
require './model/models'
require './config/db_config'
require './helpers/helper'

setting = YAML::load(File.open('./config/en.yml'))

get '/' do 
#  user = User.create(
 #                    :username => 'admin',
 #                    :password => '123456',
  #                   :tel => '15201615877')

  user = User.last
  if user 
    user.destroy!
  end
  "#{Time.now}"
  #{}"hello world"
end



