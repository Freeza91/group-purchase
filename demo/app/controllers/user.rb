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
  if user and user.password == password
    reply['message'] = 'success'
    reply['token'] = user.token
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



  # get :index, :map => '/foo/bar' do
  #   session[:foo] = 'bar'
  #   render 'index'
  # end

  # get :sample, :map => '/sample/url', :provides => [:any, :js] do
  #   case content_type
  #     when :js then ...
  #     else ...
  # end

  # get :foo, :with => :id do
  #   'Maps to url '/foo/#{params[:id]}''
  # end

  # get '/example' do
  #   'Hello world!'
  # end
  
  get '/' do
    render 'index'
  end

  get :index do 
    render 'index'
  end

end
