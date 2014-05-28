Demo::App.controllers :user do

require 'securerandom'  

post :create do

  token = SecureRandom.uuid
  nickname = params[:username]
  password = params[:password]
  tel = params[:tel]
  reply = {}

  p nickname, password, tel
  user = User.create(:nickname => nickname, :password => password, 
                     :tel => tel, :token => token, :integration => 0)
  if user and !user.nil?
    reply['message'] = 'success'
    reply['token'] = token
  else 
    reply['message'] = 'failed'
  end
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
    user.update(:nickname => username, :token => token)
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

post "/buy" do 
  token = params[:token]
  integration = params[:integration].to_i
  user = User.find_by(:token => token)
  reply = {}
  if user
    user.update(:integration => integration)
    reply['message'] = 'success'
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
    reply['username'] = user.nickname
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
