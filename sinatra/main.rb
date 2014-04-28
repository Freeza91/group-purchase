#main

require 'sinatra'
require "sinatra/activerecord"
require 'yaml'
require './model/models'
require './config/db_config'
require './helpers/helper'

setting = YAML::load(File.open('./config/en.yml'))

get '/' do 
	"hello world"
end



