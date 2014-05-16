class CreateGoods < ActiveRecord::Migration
  def self.up
    create_table :goods do |t|
      t.integer :shop_id
      t.decimal :price
      t.string :profile
      t.text :note
      t.text :service
      t.string :avatar
      t.boolean :status
      t.integer :integration
      t.timestamps
    end
  end

  def self.down
    drop_table :goods
  end
end
