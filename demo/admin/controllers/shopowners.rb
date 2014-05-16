Demo::Admin.controllers :shopowners do
  get :index do
    @title = "Shopowners"
    @shopowners = Shopowner.all
    render 'shopowners/index'
  end

  get :new do
    @title = pat(:new_title, :model => 'shopowner')
    @shopowner = Shopowner.new
    render 'shopowners/new'
  end

  post :create do
    @shopowner = Shopowner.new(params[:shopowner])
    if @shopowner.save
      @title = pat(:create_title, :model => "shopowner #{@shopowner.id}")
      flash[:success] = pat(:create_success, :model => 'Shopowner')
      params[:save_and_continue] ? redirect(url(:shopowners, :index)) : redirect(url(:shopowners, :edit, :id => @shopowner.id))
    else
      @title = pat(:create_title, :model => 'shopowner')
      flash.now[:error] = pat(:create_error, :model => 'shopowner')
      render 'shopowners/new'
    end
  end

  get :edit, :with => :id do
    @title = pat(:edit_title, :model => "shopowner #{params[:id]}")
    @shopowner = Shopowner.find(params[:id])
    if @shopowner
      render 'shopowners/edit'
    else
      flash[:warning] = pat(:create_error, :model => 'shopowner', :id => "#{params[:id]}")
      halt 404
    end
  end

  put :update, :with => :id do
    @title = pat(:update_title, :model => "shopowner #{params[:id]}")
    @shopowner = Shopowner.find(params[:id])
    if @shopowner
      if @shopowner.update_attributes(params[:shopowner])
        flash[:success] = pat(:update_success, :model => 'Shopowner', :id =>  "#{params[:id]}")
        params[:save_and_continue] ?
          redirect(url(:shopowners, :index)) :
          redirect(url(:shopowners, :edit, :id => @shopowner.id))
      else
        flash.now[:error] = pat(:update_error, :model => 'shopowner')
        render 'shopowners/edit'
      end
    else
      flash[:warning] = pat(:update_warning, :model => 'shopowner', :id => "#{params[:id]}")
      halt 404
    end
  end

  delete :destroy, :with => :id do
    @title = "Shopowners"
    shopowner = Shopowner.find(params[:id])
    if shopowner
      if shopowner.destroy
        flash[:success] = pat(:delete_success, :model => 'Shopowner', :id => "#{params[:id]}")
      else
        flash[:error] = pat(:delete_error, :model => 'shopowner')
      end
      redirect url(:shopowners, :index)
    else
      flash[:warning] = pat(:delete_warning, :model => 'shopowner', :id => "#{params[:id]}")
      halt 404
    end
  end

  delete :destroy_many do
    @title = "Shopowners"
    unless params[:shopowner_ids]
      flash[:error] = pat(:destroy_many_error, :model => 'shopowner')
      redirect(url(:shopowners, :index))
    end
    ids = params[:shopowner_ids].split(',').map(&:strip)
    shopowners = Shopowner.find(ids)
    
    if Shopowner.destroy shopowners
    
      flash[:success] = pat(:destroy_many_success, :model => 'Shopowners', :ids => "#{ids.to_sentence}")
    end
    redirect url(:shopowners, :index)
  end
end
