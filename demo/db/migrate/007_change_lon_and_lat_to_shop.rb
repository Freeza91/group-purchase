class ChangeLonAndLatToShop < ActiveRecord::Migration
  def self.up
    change_column :shops, :lon, :string
    change_column :shops, :lat, :string
  end

  def self.down
    change_column :shops, :lon, :decimal
    change_column :shops, :lat, :decimal
  end
end
