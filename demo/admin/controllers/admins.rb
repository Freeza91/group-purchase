Demo::Admin.controllers :admins do
  get :index do
    @title = "Admins"
    @admins = Admin.all
    render 'admins/index'
  end

  get :new do
    @title = pat(:new_title, :model => 'admin')
    @admin = Admin.new
    render 'admins/new'
  end

  post :create do
    @admin = Admin.new(params[:admin])
    if @admin.save
      @title = pat(:create_title, :model => "admin #{@admin.id}")
      flash[:success] = pat(:create_success, :model => 'Admin')
      params[:save_and_continue] ? redirect(url(:admins, :index)) : redirect(url(:admins, :edit, :id => @admin.id))
    else
      @title = pat(:create_title, :model => 'admin')
      flash.now[:error] = pat(:create_error, :model => 'admin')
      render 'admins/new'
    end
  end

  get :edit, :with => :id do
    @title = pat(:edit_title, :model => "admin #{params[:id]}")
    @admin = Admin.find(params[:id])
    if @admin
      render 'admins/edit'
    else
      flash[:warning] = pat(:create_error, :model => 'admin', :id => "#{params[:id]}")
      halt 404
    end
  end

  put :update, :with => :id do
    @title = pat(:update_title, :model => "admin #{params[:id]}")
    @admin = Admin.find(params[:id])
    if @admin
      if @admin.update_attributes(params[:admin])
        flash[:success] = pat(:update_success, :model => 'Admin', :id =>  "#{params[:id]}")
        params[:save_and_continue] ?
          redirect(url(:admins, :index)) :
          redirect(url(:admins, :edit, :id => @admin.id))
      else
        flash.now[:error] = pat(:update_error, :model => 'admin')
        render 'admins/edit'
      end
    else
      flash[:warning] = pat(:update_warning, :model => 'admin', :id => "#{params[:id]}")
      halt 404
    end
  end

  delete :destroy, :with => :id do
    @title = "Admins"
    admin = Admin.find(params[:id])
    if admin
      if admin != current_account && admin.destroy
        flash[:success] = pat(:delete_success, :model => 'Admin', :id => "#{params[:id]}")
      else
        flash[:error] = pat(:delete_error, :model => 'admin')
      end
      redirect url(:admins, :index)
    else
      flash[:warning] = pat(:delete_warning, :model => 'admin', :id => "#{params[:id]}")
      halt 404
    end
  end

  delete :destroy_many do
    @title = "Admins"
    unless params[:admin_ids]
      flash[:error] = pat(:destroy_many_error, :model => 'admin')
      redirect(url(:admins, :index))
    end
    ids = params[:admin_ids].split(',').map(&:strip)
    admins = Admin.find(ids)
    
    if admins.include? current_account
      flash[:error] = pat(:delete_error, :model => 'admin')
    elsif Admin.destroy admins
    
      flash[:success] = pat(:destroy_many_success, :model => 'Admins', :ids => "#{ids.to_sentence}")
    end
    redirect url(:admins, :index)
  end
end
