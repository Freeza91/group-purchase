#main

require 'sinatra'
require "sinatra/activerecord"
require 'yaml'
require 'json'
require 'securerandom'
require './model/models'
require './config/db_config'
require './helpers/helper'


setting = YAML::load(File.open('./config/en.yml'))

get '/' do 


end


post '/users/create' do

  token = SecureRandom.uuid
  username = params[:username]
  password = params[:password]
  tel = params[:tel]
  p username, password, tel
  user = User.create(:username => username, :password => password, :tel => tel, :token => token)

  reply = {}
  reply['message'] = 'success'
  reply['token'] = token
  reply.to_json
  
end

post '/users/login' do
  password = params[:password]
  tel = params[:tel]
  user = User.find_by(:tel => tel)
  reply = {}
  if user and user.password == password
    reply['message'] = 'success'
    reply['token'] = user.token
  else 
    reply['message'] = 'failed'
  end
  reply.to_json
end

put '/users/update/username' do
  username = params['username']
  token = params['token']
  reply = {}
  p 123
  user = User.find_by(:token => token)
  if user 
    token = SecureRandom.uuid
    user.update(:username => username, :token => token)
    reply['message'] = 'success'
    reply['token'] =  token
  else
    reply['message'] = 'failed'
  end
  reply.to_json

end

put '/users/update/password' do
  password = params['password']
  token = params['token']
  reply = {}

  user = User.find_by(:token => token)
  if user 
    token = SecureRandom.uuid
    user.update(:token => token, :password => password)
    reply['message'] = 'success'
    reply['token'] =  token
  else
    reply['message'] = 'failed'
  end
  reply.to_json
end

post '/users' do
  token = params[:token]
  user = User.find_by(:token => token)
  reply = {}
  if user 
    reply['message'] = 'success'
    reply['username'] = user.username
  else
    reply['message'] = 'failed'
  end
  reply.to_json
end



