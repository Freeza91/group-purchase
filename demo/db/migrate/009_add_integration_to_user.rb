class AddIntegrationToUser < ActiveRecord::Migration
  def self.up
    change_table :users do |t|
      t.integer :integration
    end
  end

  def self.down
    change_table :users do |t|
      t.remove :integration
    end
  end
end
