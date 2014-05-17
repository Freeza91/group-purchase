# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20140505134114) do

  create_table "admins", force: true do |t|
    t.string   "name"
    t.string   "surname"
    t.string   "email"
    t.string   "crypted_password"
    t.string   "role"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "comments", force: true do |t|
    t.integer  "good_id"
    t.integer  "user_id"
    t.text     "content"
    t.string   "writer"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "goods", force: true do |t|
    t.integer  "shop_id"
    t.decimal  "price",                        precision: 10, scale: 0
    t.string   "profile"
    t.text     "note",        limit: 16777215
    t.text     "service",     limit: 16777215
    t.string   "avatar"
    t.boolean  "status"
    t.integer  "integration"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "name"
  end

  create_table "shopowners", force: true do |t|
    t.string   "tel"
    t.string   "password"
    t.string   "nickname"
    t.integer  "income"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "shops", force: true do |t|
    t.string   "name"
    t.string   "address"
    t.integer  "shopowner_id"
    t.string   "lat"
    t.string   "lon"
    t.string   "shop_tel"
    t.integer  "rating"
    t.string   "category"
    t.string   "avatar"
    t.string   "profile"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  create_table "users", force: true do |t|
    t.string   "username"
    t.string   "password"
    t.string   "tel"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.string   "token"
  end

end
