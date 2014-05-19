Demo::App.controllers :user do

require 'securerandom'  

post :create do

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

post :login do
  password = params[:password]
  tel = params[:tel]
  user = User.find_by(:tel => tel)
  reply = {}
  p user
  if user and user.password == password
    reply['message'] = 'success'
    token = SecureRandom.uuid
    user.update(:token => token)
    reply['token'] = token
  else 
    reply['message'] = 'failed'
  end
  reply.to_json
end

put '/update/username' do
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
    reply['username'] = username
  else
    reply['message'] = 'failed'
  end
  reply.to_json

end

put '/update/password' do
  password = params['password']
  token = params['token']
  reply = {}

  user = User.find_by(:token => token)
  if user 
    token = SecureRandom.uuid
    user.update(:token => token, :password => password)
    reply['message'] = 'success'
    reply['token'] =  token
    reply['username'] = user.username
  else
    reply['message'] = 'failed'
  end
  reply.to_json
end

post '/' do
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

get :"update/username" do
  "index"
end

get :"update/password" do
  "index/index"
end



  get '/' do
    @user = Good.all
    reply = {}
    i = 0
    @user.each do |user|
      reply[i] = JSON.parse(user.to_json)
      i = i + 1
      #reply = tanslate_good user
    end
    reply.to_json
  end

  get :index do 
    render 'index'
  end

end
