class AddNameToGood < ActiveRecord::Migration
  def self.up
    change_table :goods do |t|
      t.string :name
    end
  end

  def self.down
    change_table :goods do |t|
      t.remove :name
    end
  end
end
