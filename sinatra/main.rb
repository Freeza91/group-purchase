#main

require 'sinatra'
require "sinatra/activerecord"
require 'yaml'
require './model/models'
require './config/db_config'
require './helpers/helper'

setting = YAML::load(File.open('./config/en.yml'))

get '/' do 
  user = User.create(
                     :username => "username", :password => "password",
                     :token => 'token'
                     )
	"hello world"
end



